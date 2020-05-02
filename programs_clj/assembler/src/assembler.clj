(ns assembler
  (:require [clojure.string :as str]))

(def predefined-symbols
  {"SP"     0 "LCL" 1 "ARG" 2 "THIS" 3 "THAT" 4
   "R0"     0 "R1" 1 "R2" 2 "R3" 3 "R4" 4 "R5" 5 "R6" 6 "R7" 7 "R8" 8
   "R9"     9 "R10" 10 "R11" 11 "R12" 12 "R13" 13 "R14" 14 "R15" 15
   "SCREEN" 16384 "KBD" 24576})

(defn exp [base exponent]
  (reduce * (repeat exponent base)))

(defn boolean->bit [b]
  (case b
    true 1
    false 0))

(defn number->binary
  "Returns the 2's representation binary value in 15 bit"
  [number]
  (loop [sum number pos 14 res ""]
    (if (>= pos 0)
      (let [pos-val (exp 2 pos)
            binary (boolean->bit (>= (- sum pos-val) 0))
            new-sum (- sum (* pos-val binary))]
        (recur new-sum (- pos 1) (str res binary)))
      res)))

(defn symbol->number [symbol symbols]
  (let [read-symbol (read-string symbol)]
    (if (int? read-symbol)
      read-symbol
      (get symbols symbol))))

(defn a-command->instruction
  "Takes an A-command and returns 0+15 binary instruction"
  [line symbol->address]
  (-> line
      (subs 1)                                              ; Remove @
      (symbol->number symbol->address)
      number->binary
      (->> (str "0"))))

(defn parse-comp
  "Takes a computation mnemonic and returns a+c1..c6"
  [mnemonic]
  (case mnemonic
    "0" "0101010"
    "1" "0111111"
    "-1" "0111010"
    "D" "0001100"
    "A" "0110000"
    "!D" "0001101"
    "!A" "0110001"
    "-D" "0001111"
    "-A" "0110011"
    "D+1" "0011111"
    "A+1" "0110111"
    "D-1" "0001110"
    "A-1" "0110010"
    "D+A" "0000010"
    "D-A" "0010011"
    "A-D" "0000111"
    "D&A" "0000000"
    "D|A" "0010101"
    "M" "1110000"
    "!M" "1110001"
    "-M" "1110011"
    "M+1" "1110111"
    "M-1" "1110010"
    "D+M" "1000010"
    "D-M" "1010011"
    "M-D" "1000111"
    "D&M" "1000000"
    "D|M" "1010101"))

(defn parse-dest
  "Takes a destination mnemonic and returns 3 instruction bits"
  [mnemonic]
  (case mnemonic
    "M" "001" "D" "010" "MD" "011" "A" "100" "AM" "101" "AD" "110" "AMD" "111" "000"))

(defn parse-jump
  "Takes a jump mnemonic and returns 3 instruction bits"
  [mnemonic]
  (case mnemonic
    "JGT" "001" "JEQ" "010" "JGE" "011" "JLT" "100" "JNE" "101" "JLE" "110" "JMP" "111" "000"))

(defn split-c-command
  "Splits a c command into dest, comp and jump mnemonics"
  [line]
  (let [index-equals (str/index-of line "=") index-semicolon (str/index-of line ";")]
    (cond
      (and index-equals index-semicolon) [(subs line 0 index-equals)
                                          (subs line (+ 1 index-equals) index-semicolon)
                                          (subs line (+ 1 index-semicolon))]
      index-equals [(subs line 0 index-equals) (subs line (+ 1 index-equals)) nil]
      index-semicolon [nil (subs line 0 index-semicolon) (subs line (+ 1 index-semicolon))])))

(defn c-command->instruction [line]
  (let [[d c j] (split-c-command line)]
    (str "111" (parse-comp c) (parse-dest d) (parse-jump j))))

(defn strip-comment [line]
  (if-let [comment-index (str/index-of line "//")]
    (subs line 0 comment-index)
    line))

(defn parse-line
  "Takes one line of assembly code and returns machine code"
  [symbol->address line]
  (case (first line)
    \@ (a-command->instruction line symbol->address)
    (c-command->instruction line)))

(defn replace-ext
  "Replace .asm with .hack"
  [path]
  (str/replace path ".asm" ".hack"))

(defn clean-lines [lines]
  (->> lines
       (map strip-comment)
       (map str/trim)
       (filter not-empty)))

(defn first-pass [lines]
  (loop [symbol->address predefined-symbols
         remaining lines
         resulting-lines []
         row 0]
    (if-let [line (first remaining)]
      (if-let [pseudo (second (re-find #"^\((.+)\)$" line))]
        (recur (assoc symbol->address pseudo row) (rest remaining) resulting-lines row)
        (recur symbol->address (rest remaining) (into resulting-lines [line]) (+ row 1)))
      [resulting-lines symbol->address])))

(defn second-pass [[lines symbol->address]]
  (loop [s->a symbol->address
         remaining lines
         result []
         free-address 16]
    (if-let [line (first remaining)]
      (let [symbol (second (re-find #"@(\D.*)" line))
            new-symbol? (and symbol (not (contains? s->a symbol)))
            s->a (if new-symbol? (assoc s->a symbol free-address) s->a)
            next-free-address (if new-symbol? (+ free-address 1) free-address)]
        (recur s->a (rest remaining) (into result [(parse-line s->a line)]) next-free-address))
      (do (println s->a) result))))

(defn parse-string [s]
  (->> s
       str/split-lines
       clean-lines
       first-pass
       second-pass
       (str/join "\n")))

(defn -main [path]
  (->> path
       slurp
       parse-string
       (spit (replace-ext path))))

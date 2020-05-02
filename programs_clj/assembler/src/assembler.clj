(ns assembler
  (:require [clojure.string :as str]))

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

(defn a-command->instruction
  "Takes an A-command and returns 0+15 binary representation"
  [line]
  (-> line
      (subs 1)                                              ; Remove @
      read-string                                           ; Convert to number
      number->binary
      (->> (str "0"))))

(defn parse-l-command [line]
  line                                                      ;; To be implemented
  )

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
    "D|M" "1010101"
    )
  )

(defn parse-dest
  "Takes a destination mnemonic and returns 3 instruction bits"
  [mnemonic]
  (case mnemonic
    "M" "001"
    "D" "010"
    "MD" "011"
    "A" "100"
    "AM" "101"
    "AD" "110"
    "AMD" "111"
    "000"))

(defn parse-jump
  "Takes a jump mnemonic and returns 3 instruction bits"
  [mnemonic]
  (case mnemonic
    "JGT" "001"
    "JEQ" "010"
    "JGE" "011"
    "JLT" "100"
    "JNE" "101"
    "JLE" "110"
    "JMP" "111"
    "000"))

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
  [line]
  (case (first line)
    \@ (a-command->instruction line)
    \( (parse-l-command line)
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

(defn parse [lines]
  (->> lines
       clean-lines
       (map parse-line)))

(defn parse-string [s]
  (->> s
       str/split-lines
       parse
       (str/join "\n")))

(defn -main [path]
  (->> path
       slurp
       parse-string
       (spit (replace-ext path))))

(ns assembler-test
  (:require [clojure.test :refer [deftest is testing]]
            [assembler :as assembler]
            [clojure.string :as str]))

(deftest test-clean-lines
  (testing "Make sure comments, whitespace and new lines are removed"
   (let [lines ["// A comment" "D=M" "D=M;JEQ // trailing comment" " 0;JMP "]
         expected ["D=M" "D=M;JEQ" "0;JMP"]]
     (is (= (assembler/clean-lines lines) expected)))))

(deftest binary-number
  (is (= (assembler/number->binary 32767) "111111111111111"))
  (is (= (assembler/number->binary 0) "000000000000000"))
  (is (= (assembler/number->binary 1) "000000000000001"))
  (is (= (assembler/number->binary 5) "000000000000101")))

(deftest test-a-command
  (is (= (assembler/a-command->instruction "@4" {}) "0000000000000100")))

(deftest test-c-command
  (is (= (assembler/c-command->instruction "D=M") "1111110000010000"))
  (is (= (assembler/c-command->instruction "D=D-M") "1111010011010000"))
  (is (= (assembler/c-command->instruction "D;JGT") "1110001100000001"))
  (is (= (assembler/c-command->instruction "AM=M-1") "1111110010101000")))

(deftest test-comp
  (is (= (assembler/parse-comp "0") "0101010"))
  (is (= (assembler/parse-comp "1") "0111111"))
  (is (= (assembler/parse-comp "-1") "0111010"))
  (is (= (assembler/parse-comp "A-D") "0000111"))
  (is (= (assembler/parse-comp "D+M") "1000010")))

(deftest test-dest
  (is (= (assembler/parse-dest "M") "001"))
  (is (= (assembler/parse-dest "D") "010"))
  (is (= (assembler/parse-dest "AMD") "111"))
  (is (= (assembler/parse-dest nil) "000"))
  (is (= (assembler/parse-dest "") "000")))

(deftest test-jump
  (is (= (assembler/parse-jump "JGT") "001"))
  (is (= (assembler/parse-jump "JLT") "100"))
  (is (= (assembler/parse-jump "JMP") "111"))
  (is (= (assembler/parse-jump nil) "000"))
  (is (= (assembler/parse-jump "") "000")))

(deftest test-split-c-command
  (is (= (assembler/split-c-command "D=D-A;JEQ") ["D" "D-A" "JEQ"]))
  (is (= (assembler/split-c-command "D=D-A") ["D" "D-A" nil]))
  (is (= (assembler/split-c-command "D-A;JEQ") [nil "D-A" "JEQ"])))

(deftest test-max-l
  (let [result (assembler/parse-string (slurp "../../official/projects/06/max/MaxL.asm"))
        expected (slurp "../../official/projects/06/max/MaxL_correct.hack")]
    (is (= (str/split-lines result) (str/split-lines expected)))))

(deftest test-pong-l
  (let [result (assembler/parse-string (slurp "../../official/projects/06/pong/PongL.asm"))
        expected (slurp "../../official/projects/06/pong/PongL_correct.hack")]
    (is (= (str/split-lines result) (str/split-lines expected)))))

(deftest test-rect-l
  (let [result (assembler/parse-string (slurp "../../official/projects/06/rect/RectL.asm"))
        expected (slurp "../../official/projects/06/rect/RectL_correct.hack")]
    (is (= (str/split-lines result) (str/split-lines expected)))))

(deftest test-rect
  (let [result (assembler/parse-string (slurp "../../official/projects/06/rect/Rect.asm"))
        expected (slurp "../../official/projects/06/rect/Rect_correct.hack")]
    (is (= (str/split-lines result) (str/split-lines expected)))))

(deftest test-add
  (let [result (assembler/parse-string (slurp "../../official/projects/06/add/Add.asm"))
        expected (slurp "../../official/projects/06/add/Add_correct.hack")]
    (is (= (str/split-lines result) (str/split-lines expected)))))

(deftest test-pong
  (let [result (assembler/parse-string (slurp "../../official/projects/06/pong/Pong.asm"))
        expected (slurp "../../official/projects/06/pong/Pong_correct.hack")]
    (is (= (str/split-lines result) (str/split-lines expected)))))

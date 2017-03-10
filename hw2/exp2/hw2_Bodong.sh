echo "Bodong Zhang Machine Learning HW2"
cd "bin"
java "T3_3_1" > hw2_result
java "T3_3_2_Bodong" >> hw2_result
java "T3_3_2_margin_perceptron_Bodong" >> hw2_result
java "T3_3_3_multiple_passes_Bodong" >> hw2_result
java "T3_3_3b_five_passes_Bodong" >> hw2_result
java "T3_3_4_aggressive_perceptron_Bodong" >> hw2_result
java "T3_3_4_aggressive_perceptron_no_shuffle_Bodong" >> hw2_result

cat "hw2_result"

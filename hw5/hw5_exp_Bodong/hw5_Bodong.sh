echo "Bodong Zhang Machine Learning HW5"
cd bin
java "exp1_svm" > hw5_result
java "exp2_3_1_2_svm" >> hw5_result
java "exp3_3_1_3_part1" >> hw5_result
java "exp3_3_1_3_part2" >> hw5_result
java "exp4_3_2_1" >> hw5_result
java "exp5_3_2_2" >> hw5_result
java "exp6_3_2_2b" >> hw5_result
cd ..
cat bin/hw5_result

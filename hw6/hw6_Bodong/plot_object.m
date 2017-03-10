clc
clear all
close all
fileID = fopen('plot_data.txt','r');

formatSpec = '%f';

A = fscanf(fileID,formatSpec);

iter=A(1:3:end,1);
object=A(3:3:end,1);
plot(iter,object);
xlabel('epoch');
ylabel('object value');
saveas(gcf,'figure.png')
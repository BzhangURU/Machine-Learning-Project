clc
clear all
close all
x=-2:0.1:2;
y=zeros(size(x));
plot(x,y,':ok')
hold on;
y_2=-2:0.1:2;
x_2=zeros(size(y_2));
plot(x_2,y_2,':ok')
hold on;

plot(-1,1,':+k');
hold on;
plot(1,-1,':+k');
hold on;
plot(1,1,':*k');
hold on;
plot(-1,-1,':*k');



clc
clear all
close all

x1=0;
y1=0;
x2=0;
y2=1;
x3=1;
y3=1;
x4=0.5;
y4=0;
x5=1;
y5=0;
x6=0.5;
y6=sqrt(3)/2;
x7=1.5;
y7=0;
x8=1;
y8=0.5;

%D1
plot(x1,y1,':+k');
axis([-0.5,2,-0.5,1.5]);
hold on;
plot(x2,y2,':+k');
hold on;
plot(x3,y3,':+k');
hold on;
plot(x5,y5,':*k');
hold on;
plot(x7,y7,':*k');

clf


%D2

plot(x1,y1,':+k');
axis([-0.5,2,-0.5,1.5]);
hold on;
plot(x5,y5,':*k');
hold on;
plot(x6,y6,':*k');
hold on;
plot(x8,y8,':*k');


clf


%D3

plot(x3,y3,':+k');
axis([-0.5,2,-0.5,1.5]);
hold on;
plot(x4,y4,':+k');
hold on;
plot(x5,y5,':*k');
hold on;
plot(x7,y7,':*k');
hold on;

clc
clear all
close all

read();









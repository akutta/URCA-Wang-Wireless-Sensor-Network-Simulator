% A = Area ( 1000 x 1000 )
% N = total Num Nodes
% lambda = N / A
%S = 4 * pi * r^3 / 3
%p_cover1 =  1 - e^(-lambda*S)


p1h = polyfit(x1h,y1h,4);
p1h_fit = polyval(p1h,x1h);

p3h = polyfit(x3h,y3h,4);
p3h_fit = polyval(p3h,x3h);

p1n = polyfit(x1n,y1n,4);
p1n_fit = polyval(p1n, x1n);

p3n = polyfit(x3n,y3n,4);
p3n_fit = polyval(p3n,x3n);

%homogeneous 1 factor
A = 1000*1000;
N = x1h + 300;
l = N / A;
S = pi * (40)^2;
pCover1h = 1 - exp(1).^(-l*S);

%homogeneous 3 factor
N = x3h + 300;  %shouldn't matter
pCover3h_0 = exp(1).^(-l*S);
pCover3h_1 = (l*S).* exp(1).^(-l*S);
pCover3h_2 = ((l*S).^2 / 2).* exp(1).^(-l*S);
pCover3h = 1 - (pCover3h_0 + pCover3h_1 + pCover3h_2);

%heterogeneous 1 factor
r1 = 120;
r2 = 40;
S1 = 3.14159 * r1 ^ 2;
S2 = 3.14159 * r2 ^ 2;
l1 = x1n / A;
l2 = 300 / A;
p1_0 = exp(1).^(-l1 * S1);
p2_0 = exp(1).^(-l2 * S2);
pCover1n = 1 - p1_0.*p2_0;

%heterogeneous 3 factor

%m = 0 is defined for heterogeneous 1 factor
m0 = p1_0.*p2_0;

%m = 1
p1_1 = (S1 * l1).*exp(1).^(-S1.*l1);
p2_1 = (S2 * l2).*exp(1).^(-S2.*l2);
m1 = p1_0.*p2_1+p1_1.*p2_0;

%m = 2
p1_2 = ((S1*l1).^2 / 2).*exp(1).^(-S1*l1);
p2_2 = ((S2*l2).^2 / 2).*exp(1).^(-S2*l2);
m2 = p1_0.*p2_2 + p1_1.*p2_1 + p1_2.*p2_0;

pCover3n = 1 - (m0+m1+m2);

hold on
%,'Display Name','Sim k=1 r1=r2=40'
plot(x1h,pCover1h,'-.','Color','c');
plot(x1h,p1h_fit,'Color','c');


plot(x3h,pCover3h,'-.','Color','g');
plot(x3h,p3h_fit,'Color','g');

plot(x1n,pCover1n,'-.','Color','r');
plot(x1n,p1n_fit,'Color','r');

plot(x1n,pCover3n,'-.','Color','m');
plot(x3n,p3n_fit,'Color','m');

h = legend('Theo.  k=1 r1=r2=40','k=1 r1=r2=40','Theo. k=3 r1=r2=40','k=3 r1=r2=40', 'Theo. k=1 r1=120 r2=40', 'k=1 r1=120 r2=40','Theo. k=3 r1=120 r2=40','k=3 r1=120 r2=40',8);
hold off
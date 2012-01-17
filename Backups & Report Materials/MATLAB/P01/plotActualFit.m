
p1h = polyfit(x1h,y1h,4);
p1h_fit = polyval(p1h,x1h);

p3h = polyfit(x3h,y3h,4);
p3h_fit = polyval(p3h,x3h);

p1n = polyfit(x1n,y1n,4);
p1n_fit = polyval(p1n, x1n);

p3n = polyfit(x3n,y3n,4);
p3n_fit = polyval(p3n,x3n);

hold on
plot(x1h,p1h_fit,'Color','c');
plot(x3h,p3h_fit,'Color','g');
plot(x1n,p1n_fit,'Color','r');
plot(x3n,p3n_fit,'Color','m');
hold off
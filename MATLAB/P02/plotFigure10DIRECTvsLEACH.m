
[X,Y] = meshgrid(0:.1:1,10:10:100);

Z1 = eDissDIRECT(X.*10 + 1 + ((Y./10-1).*11));
Z2 = eDissLEACH(X.*10+1+((Y./10-1).*11));

surf(X,Y,Z1);
hold on

surf(X,Y,Z2);

xlabel('Electronics energy (Joules/bit)')
ylabel('Network diameter (m)')
zlabel('Total energy dissipated in system (Joules)')
hold off
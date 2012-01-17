
X = 5;
Y = 10:10:100;
%PLOTTING
Z1 = eDissLEACH(X + 1 + ((Y./10-1).*11));
Z2 = eDissMTE(X+1+((Y./10-1).*11));
Z3 = eDissDIRECT(X + 1 + ((Y./10-1).*11));

pLeach = plot(Y,Z1);
hold on
pMTE = plot(Y,Z2);
pDirect = plot(Y,Z3);
xlabel('Network diameter (m)');
ylabel('Total energy dissipated in system (Joules)');

set(pDirect,'Color','blue');
set(pMTE,'Color','red');
set(pLeach,'Color','green');

legend('LEACH','MTE','Direct');

hold off
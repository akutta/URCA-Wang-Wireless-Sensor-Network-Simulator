x = plotData(:,1);
y = plotData(:,2);
z = plotData(:,3);
xlin = linspace(min(x),max(x),50);
ylin = linspace(min(y),max(y),50);
[X,Y] = meshgrid(xlin,ylin);
Z = griddata(x,y,z,X,Y,'nearest');
mesh(X,Y,Z);
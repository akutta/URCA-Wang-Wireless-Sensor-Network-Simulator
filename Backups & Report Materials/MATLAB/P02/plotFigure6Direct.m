
pDirectA = scatter(directFigure6Alive(:,1),directFigure6Alive(:,2),'o');
hold on
pDirectD = scatter(directFigure6Dead(:,1),directFigure6Dead(:,2),'.');
xlabel('X-Coordinate');
ylabel('Y-Coordinate');
legend('Alive','Dead');
hold off

pMteA = scatter(mteFigure6Alive(:,1),mteFigure6Alive(:,2),'o');
hold on
pMteD = scatter(mteFigure6Dead(:,1),mteFigure6Dead(:,2),'.');
xlabel('X-Coordinate');
ylabel('Y-Coordinate');
legend('Alive','Dead');
hold off
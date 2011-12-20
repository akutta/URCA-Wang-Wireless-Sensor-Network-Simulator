
pLeachA = scatter(leachFigure12Alive(:,1),leachFigure12Alive(:,2),'o');
hold on
pLeachD = scatter(leachFigure12Dead(:,1),leachFigure12Dead(:,2),'.');
xlabel('X-Coordinate');
ylabel('Y-Coordinate');
legend('Alive','Dead');
hold off
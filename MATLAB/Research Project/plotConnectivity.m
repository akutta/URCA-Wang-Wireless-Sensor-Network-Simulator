pSquareSingle = plot(squareSingle(:,1) .* 2,(squareSingle(:,3) - squareSingle(:,4))./squareSingle(:,3),'-.');

hold on
pTriangleSingle = plot(triangleSingle(:,1) .* 2, (triangleSingle(:,3) - triangleSingle(:,4))./triangleSingle(:,3),':');
pRandomSingle = plot(randomSingle(:,1) .* 2,(randomSingle(:,3) - randomSingle(:,4))./randomSingle(:,3));

set(pTriangleSingle,'Color','green');
set(pRandomSingle,'Color','red');

axis([45 80 0 1]);
xlabel('Communication Range');
ylabel('Percentage of Nodes Connected with Base');
title('Sensor Connectivity');
legend('Square Distribution','Triangle Distribution','Random Distribution');
hold off

pTriangleSingle = plot(triangleSingle(:,1),triangleSingle(:,5),'-.');

hold on

pTriangleIndividual = plot(triangleThreeIndividual(:,1),triangleThreeIndividual(:,5),':','LineWidth',2);
pTriangleSimultaneous = plot(triangleThreeSimultaneous(:,1),triangleThreeSimultaneous(:,5));

set(pTriangleIndividual,'Color','red');
set(pTriangleSimultaneous,'Color','green');

axis([25 55 0 1000]);
xlabel('Sensing Distance (m)');
ylabel('Average Intruder Distance (m)');
legend('Single Detection','3-Individual Detection','3-Simultaneous Detection');
title('Intruder Distance with 400 Nodes - Triangle Distribution');
hold off
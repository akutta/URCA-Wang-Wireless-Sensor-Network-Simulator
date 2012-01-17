pSquareSimultaneous = plot(squareThreeSimultaneous(:,1),squareThreeSimultaneous(:,5),'-.');

hold on
pTriangleSimultaneous = plot(triangleThreeSimultaneous(:,1),triangleThreeSimultaneous(:,5),':','LineWidth',2);
pRandomSimultaneous = plot(randomThreeSimultaneous(:,1),randomThreeSimultaneous(:,5));

set(pTriangleSimultaneous,'Color','red');
set(pRandomSimultaneous,'Color','green');


axis([45 60 0 1000]);
xlabel('Sensing Distance (m)');
ylabel('Average Intruder Distance (m)');
legend('Square Distribution','Triangle Distribution','Random Distribution');
title('Intruder Distance with 400 Nodes - Three Simultaneous Detection');
hold off
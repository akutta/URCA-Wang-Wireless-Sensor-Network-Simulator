pSquareSingle = plot(squareSingle(:,1),squareSingle(:,5),'-.');

hold on
%pSquareIndividual = plot(squareThreeIndividual(:,1),squareThreeIndividual(:,5),'-.');
%pSquareSimultaneous = plot(squareThreeSimultaneous(:,1),squareThreeSimultaneous(:,5),'-.');

pTriangleSingle = plot(triangleSingle(:,1),triangleSingle(:,5),':','LineWidth',2);
%pTriangleIndividual = plot(triangleThreeIndividual(:,1),triangleThreeIndividual(:,5),':','LineWidth',2);
%pTriangleSimultaneous = plot(triangleThreeSimultaneous(:,1),triangleThreeSimultaneous(:,5),':','LineWidth',2);

pRandomSingle = plot(randomSingle(:,1),randomSingle(:,5));


set(pTriangleSingle,'Color','red');
set(pRandomSingle,'Color','green');

%pRandomIndividual = plot(randomThreeIndividual(:,1),randomThreeIndividual(:,5));
%pRandomSimultaneous = plot(randomThreeSimultaneous(:,1),randomThreeSimultaneous(:,5));

axis([20 40 0 1000]);
xlabel('Sensing Distance (m)');
ylabel('Average Intruder Distance (m)');
legend('Square Distribution','Triangle Distribution','Random Distribution');
title('Intruder Distance with 400 Nodes - Single Detection');
hold off
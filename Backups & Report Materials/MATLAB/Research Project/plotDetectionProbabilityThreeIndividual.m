pSquareIndividual = plot(squareThreeIndividual(:,1),100 .* squareThreeIndividual(:,7)./1000,'-.');

hold on
pTriangleIndividual = plot(triangleThreeIndividual(:,1),100 .* triangleThreeIndividual(:,7)./1000,':');
pRandomIndividual = plot(randomThreeIndividual(:,1),100 .* randomThreeIndividual(:,7)./1000);

set(pTriangleIndividual,'Color','red');
set(pRandomIndividual,'Color','green');

axis([20 35 0 100]);
xlabel('Sensing Distance (m)');
ylabel('Detection Probability');
legend('Square Distribution','Triangle Distribution','Random Distribution');
title('Detection Probability with 400 Nodes - 3 Individual Detection');
hold off
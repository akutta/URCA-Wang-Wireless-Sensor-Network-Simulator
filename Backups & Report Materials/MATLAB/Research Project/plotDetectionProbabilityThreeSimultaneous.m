pSquareSimultaneous = plot(squareThreeSimultaneous(:,1),100 .* squareThreeSimultaneous(:,7)./1000,'-.');

hold on
pTriangleSimultaneous = plot(triangleThreeSimultaneous(:,1),100 .* triangleThreeSimultaneous(:,7)./1000,':');
pRandomSimultaneous = plot(randomThreeSimultaneous(:,1),100 .* randomThreeSimultaneous(:,7)./1000);

set(pTriangleSimultaneous,'Color','red');
set(pRandomSimultaneous,'Color','green');

axis([30 60 0 100]);
xlabel('Sensing Distance (m)');
ylabel('Detection Probability');
legend('Square Distribution','Triangle Distribution','Random Distribution');
title('Detection Probability with 400 Nodes - 3 Simultaneous Detection');
hold off
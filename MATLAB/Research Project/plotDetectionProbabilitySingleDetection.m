pSquareSingle = plot(squareSingle(:,1),100 .* squareSingle(:,7)./1000,'-.');

hold on
pTriangleSingle = plot(triangleSingle(:,1),100 .* triangleSingle(:,7)./1000,':');
pRandomSingle = plot(randomSingle(:,1),100 .* randomSingle(:,7)./1000);
set(pTriangleSingle,'Color','red');
set(pRandomSingle,'Color','green');

axis([15 35 0 100]);
xlabel('Sensing Distance (m)');
ylabel('Detection Probability');
legend('Square Distribution','Triangle Distribution','Random Distribution');
title('Detection Probability with 400 Nodes - Single Detection');
hold off
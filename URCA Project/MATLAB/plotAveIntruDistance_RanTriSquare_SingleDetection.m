
pSquareSingle = plot(squareSingle(:,1),squareSingle(:,5),'--','Color','blue','LineWidth',2);
hold on
grid on
pTriangleSingle = plot(triangleSingle(:,1),triangleSingle(:,5),'Color','red','LineWidth',2);
pRandomSingle = plot(randomSingle(:,1),randomSingle(:,5),'-*','Color','black','LineWidth',1);

axis([25 75 -100 1000]);
set(gca,'YTick',[0 200 400 600 800 1000]);

xlabel('Sensing Range (m)');
ylabel('Average Intrusion Distance (m)');
legend('Square-1-Detection','Triangle-1-Detection','Random-1-Detection');

hold off

subplot(2,1,1);
pSquareSingle = plot(squareSingle(:,1),squareSingle(:,5),'--','Color','blue','LineWidth',2);
hold on
grid on
pSquareK = plot(squareKIndividual(:,1),squareKIndividual(:,5),'Color','red','LineWidth',2);
pSquareKSim = plot(squareKSimultaneous(:,1),squareKSimultaneous(:,5),'-*','Color','black','LineWidth',1);

axis([25 75 -100 1000]);
set(gca,'YTick',[0 200 400 600 800 1000]);

xlabel('Sensing Range (m)');
ylabel('Average Intrusion Distance (m)');
legend('Square-1-Detection','Square-3-Joint-Detection','Square-3-Simu-Detection');


hold off


subplot(2,1,2);
pTriangleSingle = plot(triangleSingle(:,1),triangleSingle(:,5),'--','Color','blue','LineWidth',2);
hold on
grid on
pTriangleK = plot(triangleKIndividual(:,1),triangleKIndividual(:,5),'Color','red','LineWidth',2);
pTriangleKSim = plot(triangleKSimultaneous(:,1),triangleKSimultaneous(:,5),'-*','Color','black','LineWidth',1);

%Axis properties
axis([25 75 -100 1000]);
set(gca,'YTick',[0 200 400 600 800 1000]);

xlabel('Sensing Range (m)');
ylabel('Average Intrusion Distance (m)');
legend('Triangle-1-Detection','Triangle-3-Joint-Detection','Triangle-3-Simu-Detection');
hold off
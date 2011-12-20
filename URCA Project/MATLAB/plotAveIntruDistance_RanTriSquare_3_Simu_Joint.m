
subplot(2,1,1);

pSquareKSimultaneous = plot(squareKSimultaneous(:,1),squareKSimultaneous(:,5),'--','Color','blue','LineWidth',2);
hold on
grid on
pTriangleKSimultaneous = plot(triangleKSimultaneous(:,1),triangleKSimultaneous(:,5),'Color','red','LineWidth',2);
pRandomKSimultaneous = plot(randomKSimultaneous(:,1),randomKSimultaneous(:,5),'-*','Color','black','LineWidth',1);

axis([25 75 -100 1000]);
set(gca,'YTick',[0 200 400 600 800 1000]);

xlabel('Sensing Range (m)');
ylabel('Average Intrusion Distance (m)');
legend('Square-3-Simu-Detection','Triangle-3-Simu-Detection','Random-3-Simu-Detection');

hold off


subplot(2,1,2);

pSquareK = plot(squareKIndividual(:,1),squareKIndividual(:,5),'--','Color','blue','LineWidth',2);
hold on
grid on
pTriangleK = plot(triangleKIndividual(:,1),triangleKIndividual(:,5),'Color','red','LineWidth',2);
pRandomK = plot(randomKIndividual(:,1),randomKIndividual(:,5),'-*','Color','black','LineWidth',1);

axis([25 75 -100 1000]);
set(gca,'YTick',[0 200 400 600 800 1000]);

xlabel('Sensing Range (m)');
ylabel('Average Intrusion Distance (m)');
legend('Square-3-Joint-Detection','Triangle-3-Joint-Detection','Random-3-Joint-Detection');

hold off
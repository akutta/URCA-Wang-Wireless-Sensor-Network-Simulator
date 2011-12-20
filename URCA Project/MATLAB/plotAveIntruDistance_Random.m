
pRandomSingle = plot(randomSingle(:,1),randomSingle(:,5),'--','Color','blue','LineWidth',2);
hold on
grid on
pRandomK = plot(randomKIndividual(:,1),randomKIndividual(:,5),'Color','red','LineWidth',2);
pRandomKSim = plot(randomKSimultaneous(:,1),randomKSimultaneous(:,5),'-*','Color','black','LineWidth',1);

axis([25 75 -100 1000]);
set(gca,'YTick',[0 200 400 600 800 1000]);

xlabel('Sensing Range (m)');
ylabel('Average Intrusion Distance (m)');
legend('Random-1-Detection','Random-3-Joint-Detection','Random-3-Simu-Detection');

hold off
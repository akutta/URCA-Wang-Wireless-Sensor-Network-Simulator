
pFig7alive = scatter(fig7aAlive(:,1),fig7aAlive(:,2),'.');
hold on
pFig7dead = scatter(fig7aDead(:,1),fig7aDead(:,2),'x');
pFig7CH = voronoi(fig7aCH(:,1),fig7aCH(:,2),'o');
xlabel('X-Coordinate');
ylabel('Y-Coordinate');
legend('Alive','Dead','Cluster Head');
hold off
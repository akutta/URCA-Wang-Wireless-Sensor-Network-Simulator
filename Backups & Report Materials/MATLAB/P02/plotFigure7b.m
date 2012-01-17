
pFig7alive = scatter(fig7bAlive(:,1),fig7bAlive(:,2),'.');
hold on
pFig7dead = scatter(fig7bDead(:,1),fig7bDead(:,2),'x');
pFig7CH = voronoi(fig7bCH(:,1),fig7bCH(:,2),'o');
xlabel('X-Coordinate');
ylabel('Y-Coordinate');
legend('Alive','Dead','Cluster Head');
hold off
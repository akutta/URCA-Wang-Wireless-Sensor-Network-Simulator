pDirect = plot(directNumAlive(:,1),directNumAlive(:,2));
hold on
pMTE = plot(mteNumAlive(:,1),mteNumAlive(:,2));
pLEACH = plot(leachNumAlive(:,1),leachNumAlive(:,2));

set(pMTE,'Color','red')
set(pLEACH,'Color','green')
xlabel('Time steps (rounds)')
ylabel('Number of sensors still alive')
legend('Direct','MTE','LEACH')
hold off
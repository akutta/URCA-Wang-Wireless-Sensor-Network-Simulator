pDirect = plot(directNumAlive(:,1),directNumAlive(:,2));
hold on
pMTE = plot(mteNumAlive(:,1),mteNumAlive(:,2));
set(pMTE,'Color','red')
xlabel('Time steps (rounds)')
ylabel('Number of sensors still alive')
legend('Direct','MTE')
hold off

pDirect = plot(0:10:100,1,'-');
hold on
plot(leachPercentCH(:,1),leachPercentCH(:,2)./leachPercentCH(1,2));
xlabel('Percent of nodes that are cluster heads');
ylabel('Normalized energy dissipation');
legend('LEACH');
hold off
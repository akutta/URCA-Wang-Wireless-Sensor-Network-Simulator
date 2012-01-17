data1h = csvread('data1h.csv');
data3h = csvread('data3h.csv');
data1n = csvread('data1n.csv');
data3n = csvread('data3n.csv');

coverH = csvread('dataConnectivityH.csv');
coverN = csvread('dataConnectivityN.csv');

x1h = data1h(:,1);
y1h = data1h(:,2);

x3h = data3h(:,1);
y3h = data3h(:,2);

x1n = data1n(:,1);
y1n = data1n(:,2);

x3n = data3n(:,1);
y3n = data3n(:,2);

xcH = coverH(:,1);
ycH = coverH(:,2);

xcN = coverN(:,1);
ycN = coverN(:,2);
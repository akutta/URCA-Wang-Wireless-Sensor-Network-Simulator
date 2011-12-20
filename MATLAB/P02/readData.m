
[X,Y] = meshgrid(0:10,1:10);

% Figure 3
randomBoard = csvread('DIRECTAliveFigure3Data.csv');

%Figure 4 and 10
directEnergyDissipation = csvread('DIRECTEnergyDissipated.csv');
eDissDIRECT = directEnergyDissipation(:,3);

mteEnergyDissipation = csvread('MULTIHOPEnergyDissipated.csv');
eDissMTE = mteEnergyDissipation(:,3);

leachEnergyDissipation = csvread('LEACHEnergyDissipated.csv');
eDissLEACH = leachEnergyDissipation(:,3);

% Figure 5 and 11
directNumAlive = csvread('DIRECTNumberAlive.csv');
mteNumAlive = csvread('MULTIHOPNumberAlive.csv');
leachNumAlive = csvread('LEACHNumberAlive.csv');

% Figure 6
directFigure6Alive = csvread('DIRECTAliveFigure6Data.csv');
directFigure6Dead = csvread('DIRECTDeadFigure6Data.csv');
mteFigure6Alive = csvread('MULTIHOPAliveFigure6Data.csv');
mteFigure6Dead = csvread('MULTIHOPDeadFigure6Data.csv');

% Figure 7a
fig7aAlive = csvread('LeachFigure7aAlive.csv');
fig7aDead = csvread('LeachFigure7aDead.csv');
fig7aCH = csvread('LeachFigure7aCH.csv');

% Figure 7b
fig7bAlive = csvread('LeachFigure7bAlive.csv');
fig7bDead = csvread('LeachFigure7bDead.csv');
fig7bCH = csvread('LeachFigure7bCH.csv');

% Figure 8
leachPercentCH = csvread('LeachPercentCluster.csv');

% Figure 12
leachFigure12Alive = csvread('LEACHAliveFigure12Data.csv');
leachFigure12Dead = csvread('LEACHDeadFigure12Data.csv');
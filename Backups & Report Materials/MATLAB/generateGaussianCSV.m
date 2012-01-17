curIteration = 0;
for sigma=1:57
 for numIteration=0:9
    [X radius] = Gaussian(sigma,400,[-100 100]);
    var2csv(sprintf('GaussianNodes//nodes%i.csv',curIteration),X,radius,sigma);
    curIteration = curIteration + 1;
 end
end



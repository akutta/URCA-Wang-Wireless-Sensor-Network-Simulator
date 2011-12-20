

curIteration = 0;
for sigma=1:57
 for numIteration=0:9
    [X radius mean effSig] = TruncatedGaussian(sigma,[-1000 1000],[1 400]);
    fprintf('Real:\t%i\t\t%i\n',sigma,effSig);
    var2csv(sprintf('TruncatedGaussianNodes//nodes%i.csv',curIteration),X,radius,sigma);
    curIteration = curIteration + 1;
 end
end



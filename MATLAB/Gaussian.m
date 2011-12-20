
function [ x radius ] = Gaussian( sigma, maxNodes, range )
    radius = (max(range) - min(range))/2;
    x = [1 maxNodes];
    for i=1:maxNodes
        x(i) = gauss_distribution(sigma);
    end
return
end

% Assumes mu is 0  ( centered around x = 0
function f = gauss_distribution(s)
    x1 = rand();
    x2 = rand();
    y1 = sqrt(-2*log(x1))*cos(2*pi*x2);
    %y2 = sqrt(-2*log(x1))*sin(2*pi*x2);
    f = y1*s;
end


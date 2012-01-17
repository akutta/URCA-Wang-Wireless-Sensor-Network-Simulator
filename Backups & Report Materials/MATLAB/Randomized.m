function [ x radius ] = Randomized( maxNodes, range )
    radius = (max(range) - min(range))/2;
    x = [1 maxNodes];
    for i=1:maxNodes
        if ( rand() < .5 )
           x(i) = rand()*radius; 
        else
            x(i) = rand()*radius * -1;
        end
    end
    
end


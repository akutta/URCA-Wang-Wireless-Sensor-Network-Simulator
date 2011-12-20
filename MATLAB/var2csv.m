function var2csv( fileName, v1, radius, sigma )
%VAR2CSV Summary of this function goes here
%   Detailed explanation goes here
data = fopen(fileName, 'w');
fprintf(data,'%f\n',radius);
fprintf(data,'%f\n',sigma);
for z=1:size(v1, 2)
    sub1=v1(z);
    fprintf(data,'%f\n',sub1);
    %sub2=v2(z);
    %fprintf(data,'%f,%f\n',sub1,sub2);
end
fclose(data);

end


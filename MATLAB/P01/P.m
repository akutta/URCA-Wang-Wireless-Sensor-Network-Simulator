function [ output_args ] = P( m, S, density )
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here
    output_args = (((S * density).^m) ./ factorial(m)) .* exp(1).^(-S * density);

end


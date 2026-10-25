local hashKey=KEYS[1]
local skuId=ARGV[1]
local buyNumber=ARGV[2]



local remain=redis.call("HINCRBY",hashKey,skuId,-tonumber(buyNumber))
if remain<0 then
    redis.call("HINCRBY",hashKey,skuId,tonumber(buyNumber))
    return 1;
end
return 0
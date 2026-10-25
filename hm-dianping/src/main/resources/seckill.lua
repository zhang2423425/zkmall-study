
local voucherId=ARGV[1]
local userId=ARGV[2]
local orderId=ARGV[3]

local stockey='seckill:stock:'..voucherId
local orderkey='seckill:order:'..voucherId

if(tonumber(redis.call('get',stockey))<=0)then
    return 1
end
if(redis.call('sismember',orderkey,userId)==1)then
    return 2
end

redis.call('incrby',stockey,-1)
redis.call('sadd',orderkey,userId)

redis.call('xadd','stream.orders','*','voucherId',voucherId,'userId',userId,'id',orderId)

return 0


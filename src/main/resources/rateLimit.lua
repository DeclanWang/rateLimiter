local key = "rate.limit:" .. KEYS[1] --限流KEY
local permits = tonumber(ARGV[1]) --请求令牌数量
local curr_mill_second = ARGV[2] --当前请求时间戳
local rate_limit_info = redis.pcall("HMGET", key, "last_mill_second", "curr_permits", "max_permits", "rate")
local last_mill_second = rate_limit_info[1]
local curr_permits = tonumber(rate_limit_info[2])
local max_permits = 20 --- 初始化桶
local rate = 10 --- 初始化速率
local local_curr_permits = max_permits;
--- 令牌桶刚刚创建，上一次获取令牌的毫秒数为空
--- 根据和上一次向桶里添加令牌的时间和当前时间差，触发式往桶里添加令牌
--- 并且更新上一次向桶里添加令牌的时间
--- 如果向桶里添加的令牌数不足一个，则不更新上一次向桶里添加令牌的时间
if (type(last_mill_second) ~= 'boolean' and last_mill_second ~= false and last_mill_second ~= nil) then
    local reverse_permits = math.floor(((curr_mill_second - last_mill_second) / 1000) * rate)
    local expect_curr_permits = reverse_permits + curr_permits;
    local_curr_permits = math.min(expect_curr_permits, max_permits);
     --- 大于0表示不是第一次获取令牌
    if (reverse_permits > 0) then
        redis.pcall("HSET", key, "last_mill_second", curr_mill_second)
    end
else
    redis.pcall("HSET", key, "last_mill_second", curr_mill_second)
end
local result = -1
if (local_curr_permits - permits >= 0) then
    result = 1
    redis.pcall("HSET", key, "curr_permits", local_curr_permits - permits)
else
    redis.pcall("HSET", key, "curr_permits", local_curr_permits)
end
return result
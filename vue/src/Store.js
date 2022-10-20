// https://www.cnblogs.com/Free-Qifeng/p/15996001.html
    
// 存储
function setItem(params) {
    let initObj = {
        key: '',  // 存储的key
        value: '',  // 存储的值
        validity: null,  // 过期时间 单位：毫秒
        start: new Date().getTime() // 记录什么时候存储的
    }
    let options = {};
    // 合并、并处理参数
    Object.assign(options, initObj, params);
    console.log("setItem: " + options)
    localStorage.setItem(options.key, JSON.stringify(options));
}

// 取值
function getItem(key) {
    let options = JSON.parse(localStorage.getItem(key));
    if (!options || options.length == 0)
        return null;
    console.log("getItem: " + options)
    // 设置了过期时间
    if ( options.validity != null && options.validity > 0 ) {
        const date = new Date().getTime();
        // 判断是否超时
        if ( date - options.start > options.validity ) {
            //  缓存过期，清除缓存，返回false
            localStorage.removeItem(key);
            console.log("storage expire")
            return false;
        } else {
            // 存储还没到时间
            console.log("storage valid: " + options.value)
            return options.value;
        }
    } else {
        // 如果没有设置失效时间，直接返回值
        console.log("no time: " + options.value)
        return options.value;
    }
}

// 对外暴露移出缓存方法
function removeItem(key){
    localStorage.removeItem(key);
}

export default{
    setItem,
    getItem,
    removeItem
}

// https://www.cnblogs.com/Free-Qifeng/p/15996001.html
    
// Store
function setItem(params) {
    let initObj = {
        key: '',  
        value: '',  
        validity: null,  // expire time, unit: ms
        start: new Date().getTime() // when to store
    }
    let options = {};
    // merge and process
    Object.assign(options, initObj, params);
    localStorage.setItem(options.key, JSON.stringify(options));
}

// get
function getItem(key) {
    let options = JSON.parse(localStorage.getItem(key));
    if (!options || options.length == 0)
        return null;
    // judge if expire time is set
    if ( options.validity != null && options.validity > 0 ) {
        const date = new Date().getTime();
        // judge if expire
        if ( date - options.start > options.validity ) {
            //  yes, clear localstorage and return false
            localStorage.removeItem(key);
            console.log("storage expire")
            return false;
        } else {
            // not expired
            return options.value;
        }
    } else {
        // if time is not set
        return options.value;
    }
}

// remove localstorage
function removeItem(key){
    localStorage.removeItem(key);
}

export default{
    setItem,
    getItem,
    removeItem
}

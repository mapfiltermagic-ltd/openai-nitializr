const HTTP_METHODS = [
    { text: "GET", key: "GET" },
    { text: "PUT", key: "PUT" },
    { text: "POST", key: "POST" },
    { text: "DELETE", key: "DELETE" },
    { text: "PATCH", key: "PATCH" },
]

export const getHttpMethods = function getHttpMethods() {
    return HTTP_METHODS
}

export const getDefaultHttpMethod = function getDefaultHttpMethod() {
    return HTTP_METHODS.at(0).text
}

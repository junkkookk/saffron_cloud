export interface PageParam{
    current: number,
    pageSize: number
}

export interface Result<T>{
    code: number,
    msg: string,
    data: T
}

export interface PageResult<T>{
    total: number,
    content: Array<T>

}

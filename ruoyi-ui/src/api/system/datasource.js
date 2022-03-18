import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listDatasource(query) {
  return request({
    url: '/system/datasource/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getDatasource(datasourceId) {
  return request({
    url: '/system/datasource/' + datasourceId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addDatasource(data) {
  return request({
    url: '/system/datasource',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateDatasource(data) {
  return request({
    url: '/system/datasource',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delDatasource(datasourceId) {
  return request({
    url: '/system/datasource/' + datasourceId,
    method: 'delete'
  })
}

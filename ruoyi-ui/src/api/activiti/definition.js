import request from '@/utils/request'

// 查询modeler列表
export function listDefinition(query) {
  return request({
    url: '/processDefinition/list',
    method: 'get',
    params: query
  })
}

// 查询请假详细
export function getDefinitionsByInstanceId(instanceId) {
  return request({
    url: '/processDefinition/getDefinitions/' + instanceId,
    method: 'get'
  })
}

// 挂起激活转换
export function suspendOrActiveApply(data) {
  return request({
    url: '/processDefinition/suspendOrActiveApply',
    method: 'post',
    data:data
  })
}


// 删除Modeler
export function delDefinition(id) {
  return request({
    url: '/processDefinition/remove/' + id,
    method: 'delete'
  })
}

// 保存modeler
export function addDeploymentByString(data) {
  return request({
    url: '/processDefinition/addDeploymentByString',
    method: 'post',
    data:data
  })
}

// 获取xml
export function getProcessDefineXML(query) {
  return request({
    url: '/processDefinition/getDefinitionXML',
    method: 'get',
    params: query
  })
}

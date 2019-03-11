import request from 'supertest'
import { masterKey, apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Anuncio } from '.'

const app = () => express(apiRoot, routes)

let anuncio

beforeEach(async () => {
  anuncio = await Anuncio.create({})
})

test('POST /anuncios 201 (master)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: masterKey, contenido: 'test', ownerId: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.contenido).toEqual('test')
  expect(body.ownerId).toEqual('test')
})

test('POST /anuncios 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /anuncios 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /anuncios/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${anuncio.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(anuncio.id)
})

test('GET /anuncios/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /anuncios/:id 200 (master)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${anuncio.id}`)
    .send({ access_token: masterKey, contenido: 'test', ownerId: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(anuncio.id)
  expect(body.contenido).toEqual('test')
  expect(body.ownerId).toEqual('test')
})

test('PUT /anuncios/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${anuncio.id}`)
  expect(status).toBe(401)
})

test('PUT /anuncios/:id 404 (master)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: masterKey, contenido: 'test', ownerId: 'test' })
  expect(status).toBe(404)
})

test('DELETE /anuncios/:id 204 (master)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${anuncio.id}`)
    .query({ access_token: masterKey })
  expect(status).toBe(204)
})

test('DELETE /anuncios/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${anuncio.id}`)
  expect(status).toBe(401)
})

test('DELETE /anuncios/:id 404 (master)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: masterKey })
  expect(status).toBe(404)
})

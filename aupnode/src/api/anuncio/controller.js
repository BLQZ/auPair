import { success, notFound } from '../../services/response/'
import { Anuncio } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Anuncio.create(body)
    .then((anuncio) => anuncio.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Anuncio.count(query)
    .then(count => Anuncio.find(query, select, cursor)
      .then((anuncios) => ({
        count,
        rows: anuncios.map((anuncio) => anuncio.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Anuncio.findById(params.id)
    .then(notFound(res))
    .then((anuncio) => anuncio ? anuncio.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Anuncio.findById(params.id)
    .then(notFound(res))
    .then((anuncio) => anuncio ? Object.assign(anuncio, body).save() : null)
    .then((anuncio) => anuncio ? anuncio.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Anuncio.findById(params.id)
    .then(notFound(res))
    .then((anuncio) => anuncio ? anuncio.remove() : null)
    .then(success(res, 204))
    .catch(next)

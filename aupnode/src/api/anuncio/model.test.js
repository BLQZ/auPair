import { Anuncio } from '.'

let anuncio

beforeEach(async () => {
  anuncio = await Anuncio.create({ contenido: 'test', ownerId: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = anuncio.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(anuncio.id)
    expect(view.contenido).toBe(anuncio.contenido)
    expect(view.ownerId).toBe(anuncio.ownerId)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = anuncio.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(anuncio.id)
    expect(view.contenido).toBe(anuncio.contenido)
    expect(view.ownerId).toBe(anuncio.ownerId)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})

/// <reference types="vitest/globals" />
import { getHashColor } from './hashColors.ts' // 경로는 실제 위치로 변경

describe('getHashColor', () => {
  it('returns a color from the tagColors list', () => {
    const result = getHashColor('hello')
    expect(result).toMatch(/^bg-/)
  })

  it('returns consistent color for same input', () => {
    const result1 = getHashColor('tag123')
    const result2 = getHashColor('tag123')
    expect(result1).toBe(result2)
  })

  it('returns different color for different input (most likely)', () => {
    const result1 = getHashColor('tagA')
    const result2 = getHashColor('tagB')
    expect(result1).not.toBe(result2)
  })

  it('handles empty string input also', () => {
    const result = getHashColor('')
    expect(result).toMatch(/^bg-/)
  })
})

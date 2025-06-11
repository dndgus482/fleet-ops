/// <reference types="vitest/globals" />
import { formatDateTime, toRelativeDuration, toRelativeTime } from './dateTimeFormat.ts'

describe('formatDateTime', () => {
  it('formats valid ISO date', () => {
    expect(formatDateTime('2024-01-01T12:34:56Z', 'Asia/Hong_Kong')).toBe('January 1, 2024 at 20:34:56')
  })

  it('returns empty string for empty or invalid input', () => {
    expect(formatDateTime()).toBe('')
    expect(formatDateTime('')).toBe('')
    expect(formatDateTime('invalid-date')).toBe('')
  })
})

describe('toRelativeTime', () => {
  const now = new Date()

  it('returns just now if within 60 seconds', () => {
    const date = new Date(now.getTime() - 30 * 1000).toISOString()
    expect(toRelativeTime(date)).toBe('just now')
  })

  it('returns minutes ago if within an hour', () => {
    const date = new Date(now.getTime() - 10 * 60 * 1000).toISOString()
    expect(toRelativeTime(date)).toContain('minute')
  })

  it('returns hours ago if within a day', () => {
    const date = new Date(now.getTime() - 3 * 3600 * 1000).toISOString()
    expect(toRelativeTime(date)).toContain('hour')
  })

  it('returns formatted date if over a month ago', () => {
    const date = new Date(now.getTime() - 60 * 24 * 3600 * 1000).toISOString()
    expect(toRelativeTime(date)).toMatch(/\w+ \d{1,2}, \d{4}/)
  })
})

describe('toRelativeDuration', () => {
  it('returns empty string if start or end is missing', () => {
    expect(toRelativeDuration()).toBe('')
    expect(toRelativeDuration('2024-01-01')).toBe('')
    expect(toRelativeDuration(undefined, '2024-01-01')).toBe('')
  })

  it('returns seconds if duration < 60s', () => {
    const start = '2024-01-01T00:00:00Z'
    const end = '2024-01-01T00:00:45Z'
    expect(toRelativeDuration(start, end)).toBe('45 sec')
  })

  it('returns minutes if duration < 1 hour', () => {
    const start = '2024-01-01T00:00:00Z'
    const end = '2024-01-01T00:01:00Z'
    expect(toRelativeDuration(start, end)).toBe('1 min')
  })

  it('returns hours if duration < 1 day', () => {
    const start = '2024-01-01T00:00:00Z'
    const end = '2024-01-01T03:00:00Z'
    expect(toRelativeDuration(start, end)).toBe('3 hr')
  })

  it('returns days if duration >= 1 day', () => {
    const start = '2024-01-01T00:00:00Z'
    const end = '2024-01-05T00:00:00Z'
    expect(toRelativeDuration(start, end)).toBe('4 days')
  })
})

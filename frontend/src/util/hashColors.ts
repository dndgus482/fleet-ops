const tagColors = [
  '#fecaca', // red-200
  '#fed7aa', // orange-200
  '#fde68a', // amber-200
  '#fef08a', // yellow-200
  '#d9f99d', // lime-200
  '#bbf7d0', // green-200
  '#a7f3d0', // emerald-200
  '#99f6e4', // teal-200
  '#a5f3fc', // cyan-200
  '#bae6fd', // sky-200
  '#bfdbfe', // blue-200
  '#c7d2fe', // indigo-200
  '#ddd6fe', // violet-200
  '#e9d5ff', // purple-200
  '#fbcfe8', // pink-200
  '#fecdd3', // rose-200
]

export function getHashColor(tag: string): string {
  let hash = 0
  for (let i = 0; i < tag.length; i++) {
    hash = tag.charCodeAt(i) + ((hash << 5) - hash)
  }
  const index = Math.abs(hash) % tagColors.length
  return tagColors[index]
}

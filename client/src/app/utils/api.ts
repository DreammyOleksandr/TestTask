export const fetchMetricTypes = async () => {
  const response = await fetch('http://localhost:8080/metrictypes')
  if (response.status === 204) return []
  return response.json()
}

export const fetchMetricValues = async () => {
  const response = await fetch('http://localhost:8080/metricvalues')
  if (response.status === 204) return []
  return response.json()
}

export const createDefaultMetricTypes = async () => {
  const response = await fetch('http://localhost:8080/metrictypes/default', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
  })
  if (!response.ok) throw new Error('Failed to create default types')
}

export const deleteMetricTypes = async () => {
  const response = await fetch('http://localhost:8080/metrictypes', {
    method: 'DELETE',
    headers: { 'Content-Type': 'application/json' },
  })
  if (response.status !== 204) throw new Error('Failed to delete types')
}

export const fetchMetricValueById = async (id: number) => {
  const response = await fetch(`http://localhost:8080/metricvalues/${id}`)
  if (!response.ok) throw new Error('Failed to fetch metric value')
  return response.json()
}

export const createMetricValue = async (data: { value: string; relationIds: number[] }) => {
  const response = await fetch('http://localhost:8080/metricvalues', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  })
  if (!response.ok) throw new Error('Failed to create metric value')
  return response.json()
}

export const updateMetricValue = async (
  id: number,
  data: { value: string; relationIds: number[] }
) => {
  const response = await fetch(`http://localhost:8080/metricvalues/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  })
  if (!response.ok) throw new Error('Failed to update metric value')
  return response.json()
}

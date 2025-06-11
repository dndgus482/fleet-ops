<script setup lang="ts">
import { FormMode } from '@/composables/useFormMode'
import { NButton, useDialog } from 'naive-ui'
import { jobApi } from '@/api/api.ts'
import { dialogOptions } from '@/components/ui/dialogOptions.ts'
import { useAsyncFn } from '@/composables/useAsyncFn.ts'
import BaseIconButton from '@/components/ui/BaseIconButton.vue'
import { watchEffect } from 'vue'
import { defaultJob, type Job } from '@/types/job.ts'
import {
  jobColumn,
  useActiveField,
  useJobDescriptionField,
  useJobForm,
  useJobNameField,
  useJobTypeField,
  usePeriodField,
  useScriptField,
  useTargetAgentField,
} from '@/components/JobDetailSchema.ts'
import BaseFeedback from '@/components/ui/BaseFeedback.vue'

// props, emits
const { job = defaultJob(), formMode } = defineProps<{
  job: Job
  formMode: FormMode
}>()

const emit = defineEmits<{
  (e: 'save', jobId: string): void
  (e: 'cancel'): void
}>()

// external resources
const dialog = useDialog()

// reactive states
const form = useJobForm()
const jobNameField = useJobNameField()
const jobDescriptionField = useJobDescriptionField()
const jobTypeField = useJobTypeField()
const activeField = useActiveField()
const targetAgentField = useTargetAgentField()
const periodField = usePeriodField()
const scriptField = useScriptField()

// core functions
const loadField = (job: Job) => {
  jobNameField.input.value = job.jobName
  jobDescriptionField.input.value = job.jobDescription
  jobTypeField.input.value = job.jobType
  activeField.input.value = job.active
  targetAgentField.input.value = job.targetAgents ?? []
  periodField.input.value = job.period
  scriptField.input.value = job.script
}

const toPayload = (): any => {
  return {
    jobDescription: jobNameField.input.value,
    jobType: jobTypeField.input.value,
    active: activeField.input.value,
    targetAgents: targetAgentField.input.value,
    period: periodField.input.value,
    script: scriptField.input.value,
  }
}

// event handlers
const pressCancel = async () => {
  emit('cancel')
}

const saveJob = useAsyncFn(async () => {
  const { jobId } =
    formMode === FormMode.UPDATE
      ? (await jobApi.updateJob(job.jobId, toPayload())).data
      : (await jobApi.createJob(toPayload())).data

  emit('save', jobId)
})

const pressSave = async () => {
  if (!(await form.validateForm())) return

  dialog.create({
    ...dialogOptions,
    type: 'success',
    title: 'Confirm Save',
    content: 'Are you sure you want to save?',
    negativeText: 'Cancel',
    positiveText: 'Save',
    onPositiveClick: () => saveJob.execute(),
  })
}

// side effects
watchEffect(() => {
  loadField(job)
})
</script>

<template>
  <n-card>
    <n-form>
      <n-form-item
        label="Name"
        :feedback="jobNameField.inputError.value"
        :validation-status="jobNameField.inputError.value ? 'error' : undefined"
        required
      >
        <n-input
          v-model:value="jobNameField.input.value"
          show-count
          data-testid="jobNameInput"
        />
      </n-form-item>
      <n-divider />
      <n-form-item
        label="Description"
        :feedback="jobDescriptionField.inputError.value"
        :validation-status="
          jobDescriptionField.inputError.value ? 'error' : undefined
        "
      >
        <n-input
          type="textarea"
          v-model:value="jobDescriptionField.input.value"
          show-count
          data-testid="jobDescriptionInput"
        />
      </n-form-item>
      <n-divider />
      <div class="flex flex-col">
        <n-form-item
          label="Tags"
        />
        <div class="flex items-center gap-2">
          <n-select
            v-model:value="targetAgentField.targetAgentTypeField.input.value"
            :options="targetAgentField.targetAgentTypeField.types"
            data-testid="targetAgentTypeInput"
          />
          <n-form-item
            v-if="
                targetAgentField.targetAgentTypeField.input.value === 'GROUP'
              "
            :feedback="targetAgentField.error.value"
            :validation-status="
                targetAgentField.error.value ? 'error' : undefined
              "
          >
            <n-input
              v-model:value="targetAgentField.agentGroupIdField.input.value"
              placeholder="Agent Group Id"
              :status="
                  targetAgentField.agentGroupIdField.inputError.value
                    ? 'error'
                    : undefined
                "
              show-count
              data-testid="agentGroupIdInput"
            />
          </n-form-item>
          <template v-else>
            <n-form-item
              :feedback="targetAgentField.error.value"
              :validation-status="
                  targetAgentField.error.value ? 'error' : undefined
                "
            >
              <n-input
                v-model:value="targetAgentField.ipField.input.value"
                placeholder="IP"
                :status="
                    targetAgentField.ipField.inputError.value
                      ? 'error'
                      : undefined
                  "
                show-count
                data-testid="ipInput"
              />
            </n-form-item>
            <n-form-item
              :feedback="targetAgentField.error.value"
              :validation-status="
                  targetAgentField.error.value ? 'error' : undefined
                "
            >
              <n-input
                v-model:value="targetAgentField.userNameField.input.value"
                placeholder="UserName"
                :status="
                  targetAgentField.userNameField.inputError.value
                    ? 'error'
                    : undefined
                "
                show-count
                data-testid="userNameInput"
              />
            </n-form-item>
          </template>
          <base-icon-button
            @click="targetAgentField.add"
            type="primary"
            icon="lucide:plus"
          >
            Add
          </base-icon-button>
        </div>
      </div>
      <div class="flex flex-col gap-2">
        <n-data-table
          :columns="[
            jobColumn.agentGroup(),
            jobColumn.ip(),
            jobColumn.userName(),
            jobColumn.remove(targetAgentField.remove),
          ]"
          :data="targetAgentField.input.value"
        />
      </div>
      <n-divider />
      <n-form-item
        label="Schedule Period"
        :feedback="periodField.inputError.value"
        :validation-status="periodField.inputError.value ? 'error' : undefined"
      >
        <n-input
          type="textarea"
          v-model:value="periodField.input.value"
          show-count
          data-testid="periodInput"
        />
      </n-form-item>
      <n-divider />
      <n-form-item
        label="Script"
        :feedback="scriptField.inputError.value"
        :validation-status="scriptField.inputError.value ? 'error' : undefined"
      >
        <n-input
          type="textarea"
          v-model:value="scriptField.input.value"
          show-count
          data-testid="scriptInput"
        />
      </n-form-item>
      <n-divider />
      <div class="flex justify-end gap-2">
        <n-button @click="pressCancel" data-testid="cancelButton">
          Cancel
        </n-button>
        <base-icon-button
          type="primary"
          @click="pressSave"
          :loading="form.meta.value.pending || saveJob.isLoading.value"
          icon="lucide:save"
          data-testid="saveButton"
        >
          Save
        </base-icon-button>
      </div>
    </n-form>
  </n-card>
</template>
